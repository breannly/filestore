package com.filestore.filestore.mapper;

import com.filestore.filestore.TestDataUtils;
import com.filestore.filestore.dto.UserNewDto;
import com.filestore.filestore.dto.UserUpdateDto;
import com.filestore.filestore.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    public void mapUserNewDtoTest() {
        UserNewDto userNewDto = TestDataUtils.createUserNewDto();

        User user = userMapper.map(userNewDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(userNewDto.username(), user.getUsername());
        Assertions.assertEquals(userNewDto.password(), user.getPassword());
    }

    @Test
    public void mapUserUpdateDtoTest() {
        UserUpdateDto userUpdateDto = TestDataUtils.createUpdateDto();

        User user = userMapper.map(userUpdateDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(userUpdateDto.password(), user.getPassword());
    }

}