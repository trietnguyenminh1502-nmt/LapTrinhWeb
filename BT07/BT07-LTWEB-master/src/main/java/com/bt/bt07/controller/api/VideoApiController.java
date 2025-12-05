package com.bt.bt07.controller.api;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bt.bt07.model.Response;
import com.bt.bt07.model.Video;
import com.bt.bt07.service.IVideoService;
import com.bt.bt07.service.IStorageService;

@RestController
@RequestMapping(path = "/api/video")
public class VideoApiController {

    @Autowired
    private IVideoService videoService;

    @Autowired
    private IStorageService storageService;

    // API: Lấy danh sách Video (Có phân trang & Tìm kiếm)
    @GetMapping
    public ResponseEntity<?> getAllVideos(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Video> resultPage;

        if (keyword != null && !keyword.isEmpty()) {
            resultPage = videoService.findByTitleContaining(keyword, pageable);
        } else {
            resultPage = videoService.findAll(pageable);
        }

        return new ResponseEntity<>(new Response(true, "Thành công", resultPage), HttpStatus.OK);
    }

    // API: Thêm Video mới (Upload Poster)
    @PostMapping(path = "/add")
    public ResponseEntity<?> addVideo(
            @Validated @RequestParam("title") String title,
            @RequestParam(value = "poster", required = false) MultipartFile poster,
            @RequestParam("description") String description,
            @RequestParam(value = "active", defaultValue = "true") Boolean active) {

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setActive(active);
        video.setViews(0);

        // Xử lý upload file
        if (poster != null && !poster.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String fileName = storageService.getStorageFilename(poster, uuid);
            storageService.store(poster, fileName);
            video.setPoster(fileName);
        } else {
            video.setPoster("default.png");
        }

        videoService.save(video);
        return new ResponseEntity<>(new Response(true, "Thêm thành công", video), HttpStatus.OK);
    }

    // API: Lấy chi tiết Video theo ID (để hiển thị lên Modal sửa)
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable("id") Long id) {
        Optional<Video> optVideo = videoService.findById(id);
        if (optVideo.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Tìm thấy dữ liệu", optVideo.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(false, "Không tìm thấy", null), HttpStatus.NOT_FOUND);
    }

    // API: Cập nhật Video
    @PutMapping(path = "/update")
    public ResponseEntity<?> updateVideo(
            @Validated @RequestParam("videoId") Long videoId,
            @Validated @RequestParam("title") String title,
            @RequestParam(value = "poster", required = false) MultipartFile poster,
            @RequestParam("description") String description,
            @RequestParam("active") Boolean active) {

        Optional<Video> optVideo = videoService.findById(videoId);
        if (optVideo.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Video", null), HttpStatus.NOT_FOUND);
        }

        Video video = optVideo.get();
        video.setTitle(title);
        video.setDescription(description);
        video.setActive(active);

        if (poster != null && !poster.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String fileName = storageService.getStorageFilename(poster, uuid);
            storageService.store(poster, fileName);
            video.setPoster(fileName);
        }

        videoService.save(video);
        return new ResponseEntity<>(new Response(true, "Cập nhật thành công", video), HttpStatus.OK);
    }

    // API: Xóa Video
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable("id") Long id) {
        Optional<Video> optVideo = videoService.findById(id);
        if (optVideo.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Video", null), HttpStatus.NOT_FOUND);
        }

        // Xóa file ảnh cũ nếu không phải ảnh mặc định (Tùy chọn)
        try {
            if (optVideo.get().getPoster() != null && !optVideo.get().getPoster().equals("default.png")) {
                storageService.delete(optVideo.get().getPoster());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        videoService.deleteById(id);
        return new ResponseEntity<>(new Response(true, "Xóa thành công", null), HttpStatus.OK);
    }
}