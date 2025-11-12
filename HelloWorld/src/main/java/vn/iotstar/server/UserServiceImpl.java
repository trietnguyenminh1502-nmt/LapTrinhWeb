package vn.iotstar.server;

import vn.iotstar.dao.userDao;
import vn.iotstar.model.User;

public class UserServiceImpl implements userService{

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		User user = this.get(username);
		if (user != null && password.equals(user.getPassWord())) {
			return user;
		}

		return null;
	}

	@Override
	public User get(String username) {
		// TODO Auto-generated method stub
		try {
			return userDao.get(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
