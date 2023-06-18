package com.moigae.application.component.user.domain;

import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final String PHONE = "010";
    private static final String EMAIL = "email";

    @Test
    @DisplayName("유저 도메인 생성 테스트")
    void 유저_도메인_생성_테스트() {
        //given & when
        User user = createUser();
        //then
        유저_도메인_생성_검증(user);
    }

    @Test
    @DisplayName("회원_탈퇴_테스트")
    void 회원_탈퇴_테스트() {
        //given
        User user = createUser();
        //when
        user.deactivateUser();
        //then
        assertThat(user.isFlag()).isEqualTo(false);
        assertThat(user.deactivateUser()).isNotNull();
    }

    @Test
    @DisplayName("유저 도메인 setter 메서드 테스트")
    void 유저_도메인_setter_메서드_테스트() {
        //given
        User user = new User();
        Address newAddress = new Address("Busan", "678-90");
        LocalDate newBirth = LocalDate.of(2000, 1, 1);
        LocalDateTime newDeactivateAt = LocalDateTime.now();
        //when
        유저_도메인_setter_메서드_조건(user, newAddress, newBirth, newDeactivateAt);
        //then
        유저_도메인_setter_메서드_검증(user, newAddress, newBirth, newDeactivateAt);
    }

    @Test
    @DisplayName("유저 도메인 toString 메서드 테스트")
    void 유저_도메인_toString_메서드_테스트() {
        //given
        User user = createUser();
        String expectedString = toStringSetUp(user);
        //when
        String actualString = user.toString();
        //then
        assertThat(actualString).isEqualTo(expectedString);
    }

    private static User createUser() {
        return User.builder()
                .userName("홍정완")
                .password("패스워드")
                .gender(Gender.MAN)
                .phone(PHONE)
                .account("계좌")
                .hostIntroduction("호스트 자기소개")
                .email(EMAIL)
                .userRole(UserRole.USER)
                .flag(true)
                .deactivateAt(null)
                .build();
    }

    private static void 유저_도메인_생성_검증(User user) {
        assertThat(user.getUserName()).isEqualTo("홍정완");
        assertThat(user.getPassword()).isEqualTo("패스워드");
        assertThat(user.getGender()).isEqualTo(Gender.MAN);
        assertThat(user.getPhone()).isEqualTo(PHONE);
        assertThat(user.getAccount()).isEqualTo("계좌");
        assertThat(user.getHostIntroduction()).isEqualTo("호스트 자기소개");
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getUserRole()).isEqualTo(UserRole.USER);
        assertThat(user.isFlag()).isEqualTo(true);
    }

    private static void 유저_도메인_setter_메서드_조건(User user, Address newAddress, LocalDate newBirth, LocalDateTime newDeactivateAt) {
        user.setId("id");
        user.setUserName("홍길동");
        user.setPassword("새패스워드");
        user.setGender(Gender.WOMAN);
        user.setPhone("020");
        user.setBirth(newBirth);
        user.setAddress(newAddress);
        user.setAccount("새계좌");
        user.setHostIntroduction("새로운 호스트 소개");
        user.setEmail("newEmail");
        user.setUserRole(UserRole.ADMIN);
        user.setFlag(false);
        user.setDeactivateAt(newDeactivateAt);
    }

    private static void 유저_도메인_setter_메서드_검증(User user, Address newAddress, LocalDate newBirth, LocalDateTime newDeactivateAt) {
        assertThat(user.getId()).isEqualTo("id");
        assertThat(user.getUserName()).isEqualTo("홍길동");
        assertThat(user.getPassword()).isEqualTo("새패스워드");
        assertThat(user.getGender()).isEqualTo(Gender.WOMAN);
        assertThat(user.getPhone()).isEqualTo("020");
        assertThat(user.getBirth()).isEqualTo(newBirth);
        assertThat(user.getAddress()).isEqualTo(newAddress);
        assertThat(user.getAccount()).isEqualTo("새계좌");
        assertThat(user.getHostIntroduction()).isEqualTo("새로운 호스트 소개");
        assertThat(user.getEmail()).isEqualTo("newEmail");
        assertThat(user.getUserRole()).isEqualTo(UserRole.ADMIN);
        assertThat(user.isFlag()).isEqualTo(false);
        assertThat(user.getDeactivateAt()).isEqualTo(newDeactivateAt);
    }

    private static String toStringSetUp(User user) {
        return "User(" +
                "id=" + user.getId() +
                ", userName=홍정완" +
                ", password=패스워드" +
                ", gender=MAN" +
                ", phone=010" +
                ", birth=" + user.getBirth() +
                ", address=" + user.getAddress() +
                ", account=계좌" +
                ", hostIntroduction=호스트 자기소개" +
                ", email=email" +
                ", userRole=USER" +
                ", flag=true" +
                ", deactivateAt=" + user.getDeactivateAt() + ")";
    }
}