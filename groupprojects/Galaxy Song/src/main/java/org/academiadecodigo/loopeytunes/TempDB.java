package org.academiadecodigo.loopeytunes;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TempDB {
    private Dog dog;

    //do the beans
    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Dog getDog() {
        return dog;
    }
}
