package spring.aop.advice1;

/**
 * @author:ben.gu
 * @Date:2020/3/14 2:11 PM
 */
public interface UserService {
    User createUser(String firstName, String lastName, int age);

    User queryUser();
}
