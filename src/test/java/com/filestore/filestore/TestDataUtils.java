package com.filestore.filestore;

import com.filestore.filestore.dto.UserNewDto;
import com.filestore.filestore.dto.UserUpdateDto;
import com.filestore.filestore.entity.Event;
import com.filestore.filestore.entity.User;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class TestDataUtils {

    private static final EasyRandomParameters easyRandomParameters = new EasyRandomParameters().collectionSizeRange(0, 1);
    private static final EasyRandom easyRandom = new EasyRandom(easyRandomParameters);

    public static Event createEvent() {
        return easyRandom.nextObject(Event.class);
    }

    public static User createUser() {
        return easyRandom.nextObject(User.class);
    }

    public static UserNewDto createUserNewDto() {
        return new UserNewDto(easyRandom.nextObject(String.class), easyRandom.nextObject(String.class));
    }

    public static UserUpdateDto createUpdateDto() {
        return new UserUpdateDto(easyRandom.nextObject(String.class));
    }
}
