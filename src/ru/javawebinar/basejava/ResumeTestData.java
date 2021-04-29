package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ExperienceDescription;
import ru.javawebinar.basejava.model.ExperienceDescription2;
import ru.javawebinar.basejava.model.Resume;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {

    private static String lineSeparator = System.lineSeparator();

    public static void main(String[] args) {

        List<String> achievementsContent = new ArrayList<>(Arrays.asList(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                        "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                        "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                        "Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция " +
                        "с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.", "Налаживание процесса разработки " +
                        "и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                        "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка " +
                        "SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, " +
                        "GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base " +
                        "архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                        "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                        "мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, " +
                        "Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        ));

        List<ExperienceDescription> experience2 = new ArrayList<>(Arrays.asList(
                ExperienceDescription2.builder()
                        .withCompanyName("Java Online Projects")
                .withCompanyWebSite("https://javaops.ru/")
                .withStartDate(LocalDate.of(2013,10, 1))
                .withEndDate(LocalDate.now())
                .withPosition("Автор проекта")
                .withDescription("Создание, организация и проведение Java онлайн проектов и стажировок.")
                .build(),

                ExperienceDescription2.builder()
                        .withCompanyName("Wrike")
                        .withCompanyWebSite("https://www.wrike.com/")
                        .withStartDate(LocalDate.of(2014,10, 1))
                        .withEndDate(LocalDate.of(2016,1,1))
                        .withPosition("Старший разработчик (backend)")
                        .withDescription("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                                "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
                        .build()


        ));



        Resume resume01 = Resume.builder()
//                .withUuid("uuid01")
                .withFullName("Григорий Кислин")
                .withObjective("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям")
                .withPersonal("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры.")
                .withAchievements(achievementsContent)
                .withExperience(experience2)
//                .withEducation("")
                .build();

        System.out.println(resume01);
    }
}
