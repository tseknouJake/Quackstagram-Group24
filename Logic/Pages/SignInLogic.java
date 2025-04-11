package Logic.Pages;
import Logic.LogicClass;
import ParameterClasses.User;

/**
 * Class that handles the logic of SignInUI
 */
public class SignInLogic extends LogicClass{

    /**
     * Signs in a user
     * @param username
     * @param password
     * @return the new user 
     * @throws Exception - in case the user does not exist / in case the password is incorrect
     */
    public static User signInUser(String username, String password) throws Exception{
        User user = getUser(username);
        if (!userExists(user)){
            System.out.println("user does not exist");
            throw new Exception("User does not exist. Please sign up.");
        }
        if (!verifyCredentials(user, password)){
            System.out.println("wrong pass");
            throw new Exception("Incorrect password. Please try again.");
        }

        System.out.println(username + " logged in");
      
        saveCurrUserInformation(user);
        return user;

    }

    /**
     * Verifies user credentials
     * @param user
     * @param password
     * @return true if valid, false otherwise
     */
    private static boolean verifyCredentials(User user, String password) {
        return user.ValidatePassword(password);

    }


}
