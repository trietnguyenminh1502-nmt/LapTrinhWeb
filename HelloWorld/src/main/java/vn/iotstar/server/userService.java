package vn.iotstar.server;

import vn.iotstar.model.User;

public interface userService {
	User login(String username, String password);
	User get(String username);

}
