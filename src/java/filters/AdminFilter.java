package filters;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import domainmodel.Role;
import domainmodel.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kevin
 */
public class AdminFilter implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpSession session = ((HttpServletRequest)request).getSession();
        
        UserDB userDB = new UserDB();
        
        String username = (String) session.getAttribute("username");
        
        try{
        User user = userDB.getUser(username);
        
        Role role = user.getRole();
        
        if (role.getRoleName().equalsIgnoreCase("admin")) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).sendRedirect("login");
        }
        } catch(NotesDBException e){
            
        }
    }

    @Override
    public void destroy() {        
    }

    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }
}