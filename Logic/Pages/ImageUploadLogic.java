package Logic.Pages;

import java.util.function.Predicate;

import Logic.LogicClass;
import ParameterClasses.Post;
import ParameterClasses.User;
import TableManaging.DB;

/**
 * Class that handles the logic of ImageUploadUI
 */
public class ImageUploadLogic extends LogicClass{

    /**
     * Recieves a post pbject and saves it in the tables using the database
     */
    public static void saveImage(Post post) {

        // updates the POSTS table - adds the new post
        DB.POSTS.insertRow(post, true);
        
        String username = post.getUsername();
        User user = getUser(username);
        user = user.addPost();

        // updates the users number of posts in both the table USERS and in CURR_USER
        Predicate<User> condition = tmpUser -> tmpUser.getUsername().equals(username);
        DB.USERS.updateRows(condition, user);

        saveCurrUserInformation(user);
    }

}
