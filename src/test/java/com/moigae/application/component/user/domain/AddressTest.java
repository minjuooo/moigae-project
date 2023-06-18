package com.moigae.application.component.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {
    private static final String DETAIL = "123-45";
    private static final String CITY = "Seoul";
    private static final String UPDATE_CITY = "Incheon";
    private static final String UPDATE_DETAIL = "111-22";

    private Address address;

    @BeforeEach
    public void setUp() {
        address = Address.of(CITY, DETAIL);
    }

    @Test
    @DisplayName("Address 객체 기본 생성자 생성 테스트")
    public void noArgsConstructor() {
        //given & when
        Address emptyAddress = new Address();
        //then
        assertThat(emptyAddress).isNotNull();
        assertThat(emptyAddress.getCity()).isNull();
        assertThat(emptyAddress.getDetail()).isNull();
    }

    @Test
    @DisplayName("Address 객체 생성자 생성 테스트")
    public void allArgsConstructor() {
        //given & when & then
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isEqualTo(CITY);
        assertThat(address.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    @DisplayName("도시 - 업데이트 로직 테스트")
    public void testUpdateCity() {
        //when
        address.updateCity(UPDATE_CITY);
        //then
        assertThat(address.getCity()).isEqualTo(UPDATE_CITY);
    }

    @Test
    @DisplayName("상세 주소 - 업데이트 로직 태스트")
    public void testUpdateDetail() {
        //when
        address.updateDetail(UPDATE_DETAIL);
        //then
        assertThat(address.getDetail()).isEqualTo(UPDATE_DETAIL);
    }

    @Test
    @DisplayName("EqualsAndHashCode 테스트")
    public void testEqualsAndHashCode() {
        //given
        Address sameAddress = Address.of(CITY, DETAIL);
        Address differentCity = Address.of("", DETAIL);

        //when & then
        assertThat(address).isEqualTo(sameAddress);
        assertThat(address).isNotEqualTo(differentCity);

        //when & then
        assertThat(address.hashCode()).isEqualTo(sameAddress.hashCode());
        assertThat(address.hashCode()).isNotEqualTo(differentCity.hashCode());
    }
}