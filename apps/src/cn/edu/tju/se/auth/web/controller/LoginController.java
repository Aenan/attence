package cn.edu.tju.se.auth.web.controller;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.tju.se.auth.dao.UserDao;
import cn.edu.tju.se.auth.domain.User;
import cn.edu.tju.se.auth.service.UserService;
import cn.edu.tju.se.base.cons.CommonConstant;
import cn.edu.tju.se.base.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * <br>
 * <b>类描述:</b>
 * <pre>
 *   景区登录控制器，处理登录验证、注销等操作
 * </pre>
 * @see
 * @since
 */
@Controller
@RequestMapping("/auth")
public class LoginController extends BaseController {
	/**
	 * 自动注入
	 */
	@Autowired
	private UserService userService;

	
	/**
	 * 用户登录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(method=RequestMethod.POST, value="/login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userService.findUserByUserName(userName);
		
		if (user == null) {
			request.setAttribute("errorMsg", "用户名不存在");
			return "forward:/login.html";
		} else if (!userService.checkPassword(password, user)) {
			request.setAttribute("errorMsg", "用户密码不正确");
			return "forward:/login.html";
		} else {
			setSessionUser(request,user);
			return "forward:/welcome.html";
		}
	}

    /**
     *登录注销
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(CommonConstant.USER_CONTEXT);
        return "forward:/login.html";
    }
    
    @RequestMapping(method=RequestMethod.POST, value="/register")
    public String register(HttpServletRequest request, HttpServletResponse response){
    	User user = new User();
    	user.setUsername(request.getParameter("username"));
    	user.setPassword(request.getParameter("password"));
    	userService.save(user);
    	return "forward:/login.html";
    }
}
