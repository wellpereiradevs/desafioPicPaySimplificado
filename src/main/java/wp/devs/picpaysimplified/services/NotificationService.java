package wp.devs.picpaysimplified.services;

import wp.devs.picpaysimplified.domain.user.User;

public class NotificationService {
    public void sendNotification(User user, String message){
        String email = user.getEmail();
        System.out.println(email+message);
    }
}
