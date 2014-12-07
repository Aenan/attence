package cn.edu.tju.se.auth.service;

import cn.edu.tju.se.auth.dao.UserDao;
import cn.edu.tju.se.auth.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private static final String SITE_WIDE_SECRET = "TJU";
	private static final PasswordEncoder encoder = new StandardPasswordEncoder(
			SITE_WIDE_SECRET);
	
	@Autowired
	private UserDao userDao;
	
	public User findUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

    public User getUserById(int userId) {
        return userDao.get(userId);
    }

    public void save(User user){
    	user.setPassword(encoder.encode(user.getPassword()));
    	userDao.save(user);
    }
    
    public boolean checkPassword(String password,User user){
    	return(encoder.matches(password, user.getPassword()));
    }

    public static void main(String[] argv){
    	System.out.println(encoder.encode("123"));
    }
}
