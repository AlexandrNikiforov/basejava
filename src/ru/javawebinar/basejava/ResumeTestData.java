package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactName;
import ru.javawebinar.basejava.model.Organization;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionName;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class ResumeTestData {
    private static final List<String> ACHIEVEMENTS_CONTENT = new ArrayList<>(Arrays.asList(
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

    private static final List<String> QUALIFICATIONS_CONTENT = new ArrayList<>(Arrays.asList(
            "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
            "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
            "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
            "MySQL, SQLite, MS SQL, HSQLDB",
            "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
            "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
            "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
            "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, " +
                    "Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                    "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium " +
                    "(htmlelements).",
            "Python: Django.",
            "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
            "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
            "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, " +
                    "MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, " +
                    "OAuth1, OAuth2, JWT.",
            "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
            "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport," +
                    " OpenCmis, Bonita, pgBouncer.",
            "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных " +
                    "шаблонов, UML, функционального программирования",
            "Родной русский, английский \"upper intermediate\""
    ));

    private static final List<Organization> EXPERIENCE_CONTENT = new ArrayList<>(Arrays.asList(
            Organization.builder()
                    .homePage("Java Online Projects", "https://javaops.ru/")
                    .withExperience(new ArrayList<>(Arrays.asList(
                            Organization.Position.experienceItemBuilder()
                                    .withStartDate(2013, Month.OCTOBER)
                                    .withoutEndDate()
                                    .withTitle("Автор проекта")
                                    .withDescription("Создание, организация и проведение Java онлайн проектов и стажировок.")
                                    .build()
                    )))
                    .build(),

            Organization.builder()
                    .homePage("Wrike", "https://www.wrike.com/")
                    .withExperience(
                            new ArrayList<>(Arrays.asList(
                                    Organization.Position.experienceItemBuilder()
                                            .withStartDate(2014, Month.OCTOBER)
                                            .withEndDate(2016, Month.JANUARY)
                                            .withTitle("Старший разработчик (backend)")
                                            .withDescription("Проектирование и разработка онлайн платформы управления проектами Wrike" +
                                                    "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                                                    "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
                                            .build()
                            )))
                    .build(),

            Organization.builder()
                    .homePage("RIT Center", null)
                    .withExperience(
                            new ArrayList<>(Arrays.asList(
                                    Organization.Position.experienceItemBuilder()
                                            .withStartDate(2012, Month.APRIL)
                                            .withEndDate(2014, Month.OCTOBER)
                                            .withTitle("Java архитектор")
                                            .withDescription("Организация процесса разработки системы ERP для разных окружений: релизная " +
                                                    "политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                                                    "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной " +
                                                    "части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                                                    "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco " +
                                                    "JLAN для online редактирование из браузера документов MS Office. Maven + plugin " +
                                                    "development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, " +
                                                    "OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, " +
                                                    "PL/Python")
                                            .build()
                            )))
                    .build(),

            Organization.builder()
                    .homePage("Luxoft (Deutsche Bank)", "https://luxoft.com")
                    .withExperience(
                            new ArrayList<>(Arrays.asList(
                                    Organization.Position.experienceItemBuilder()


                                            .withStartDate(2010, Month.DECEMBER)
                                            .withEndDate(2012, Month.APRIL)
                                            .withTitle("Ведущий программист")
                                            .withDescription("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, " +
                                                    "Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной " +
                                                    "части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа " +
                                                    "результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, " +
                                                    "ExtGWT (GXT), Highstock, Commet, HTML5.")
                                            .build()))
                    )
                    .build(),

            Organization.builder()
                    .homePage("Yota", "https://www.yota.ru/")
                    .withExperience(
                            new ArrayList<>(Arrays.asList(
                                    Organization.Position.experienceItemBuilder()

                                            .withStartDate(2008, Month.JUNE)
                                            .withEndDate(2012, Month.OCTOBER)
                                            .withTitle("Ведущий специалист")
                                            .withDescription("Дизайн и имплементация Java EE фреймворка для отдела " +
                                                    "\"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, " +
                                                    "JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга " +
                                                    "фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")
                                            .build()
                            )))
                    .build()
    ));

    private static final List<Organization> EDUCATION_CONTENT = new ArrayList<>(Arrays.asList(
            Organization.builder()
                    .homePage("Coursera", "https://www.coursera.org/learn/progfun1")
                    .withExperience(
                            new ArrayList<>(Arrays.asList(
                                    Organization.Position.experienceItemBuilder()

                                            .withStartDate(2013, Month.MARCH)
                                            .withEndDate(2013, Month.MAY)
                                            .withTitle("\"Functional Programming Principles in Scala\" by Martin Odersky")
                                            .withDescription(null).build()
                            )))
                    .build(),

            Organization.builder()
                    .homePage("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366")
                    .withExperience(
                            new ArrayList<>(Arrays.asList(
                                    Organization.Position.experienceItemBuilder()
                                            .withStartDate(2011, Month.MARCH)
                                            .withEndDate(2011, Month.APRIL)
                                            .withTitle("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"")
                                            .withDescription(null)
                                            .build()
                            )))
                    .build(),

            Organization.builder()
                    .homePage("Siemens AG", "http://www.siemens.ru/")
                    .withExperience(
                            new ArrayList<>(Arrays.asList(
                                    Organization.Position.experienceItemBuilder()
                                            .withStartDate(2005, Month.JANUARY)
                                            .withEndDate(2005, Month.APRIL)
                                            .withTitle("3 месяца обучения мобильным IN сетям (Берлин)")
                                            .withDescription(null)
                                            .build()
                            )))
                    .build(),

            Organization.builder()
                    .homePage("Санкт-Петербургский национальный исследовательский университет " +
                            "информационных технологий, механики и оптики", "http://www.ifmo.ru/")
                    .withExperience(
                            new ArrayList<>(Arrays.asList(

                                    Organization.Position.experienceItemBuilder()
                                            .withStartDate(1993, Month.SEPTEMBER)
                                            .withEndDate(1996, Month.JULY)
                                            .withTitle("Аспирантура (программист С, С++)")
                                            .withDescription(null)
                                            .build(),

                                    Organization.Position.experienceItemBuilder()
                                            .withStartDate(1987, Month.JULY)
                                            .withEndDate(1993, Month.JULY)
                                            .withTitle("3 месяца обучения мобильным IN сетям (Берлин)")
                                            .withDescription(null)
                                            .build()
                            )))
                    .build()
    ));
    public static final String PHONE_NUMBER = "+7(921)855-0482";
    public static final String SKYPE = "grigory.kislin";
    public static final String EMAIL = "gkislin@yandex.ru";
    public static final String LINKEDIN = "https://www.linkedin.com/in/gkislin";
    public static final String GITHUB = "https://github.com/gkislin";
    public static final String STACKOVERFLOW = "https://stackoverflow.com/users/548473";
    public static final String HOMEPAGE = "http://gkislin.ru/";
    public static final String OBJECTIVE_CONTENT = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям";
    public static final String PERSONAL_CONTENT = "Аналитический склад ума, сильная логика, креативность, инициативность. " +
            "Пурист кода и архитектуры.";


    public static void main(String[] args) {
        Resume resume01 = Resume.builder()
                .withContactsImplementation(new EnumMap<>(ContactName.class))
                .withSectionsImplementation(new EnumMap<>(SectionName.class))
//                .withUuid("uuid01")
                .withFullName("Григорий Кислин")
                .withPhoneNumber(PHONE_NUMBER)
                .withSkype(SKYPE)
                .withEmail(EMAIL)
                .withLinkedIn(LINKEDIN)
                .withGitHubProfile(GITHUB)
                .withStackoverflow(STACKOVERFLOW)
                .withHomePage(HOMEPAGE)
                .withObjective(OBJECTIVE_CONTENT)
                .withPersonal(PERSONAL_CONTENT)
                .withAchievements(ACHIEVEMENTS_CONTENT)
                .withQualifications(QUALIFICATIONS_CONTENT)
                .withExperience(EXPERIENCE_CONTENT)
                .withEducation(EDUCATION_CONTENT)
                .build();

        System.out.println(resume01);
    }

    public static Resume createResume(String uuid, String fullName) {
        return Resume.builder()
                .withContactsImplementation(new EnumMap<>(ContactName.class))
                .withSectionsImplementation(new EnumMap<>(SectionName.class))
                .withUuid(uuid)
                .withFullName(fullName)
                .withPhoneNumber(PHONE_NUMBER)
                .withSkype(SKYPE)
                .withEmail(EMAIL)
                .withLinkedIn(LINKEDIN)
                .withGitHubProfile(GITHUB)
                .withStackoverflow(STACKOVERFLOW)
                .withHomePage(HOMEPAGE)
                .withObjective(OBJECTIVE_CONTENT)
                .withPersonal(PERSONAL_CONTENT)
                .withAchievements(ACHIEVEMENTS_CONTENT)
                .withQualifications(QUALIFICATIONS_CONTENT)
                .withExperience(EXPERIENCE_CONTENT)
                .withEducation(EDUCATION_CONTENT)
                .build();
    }
}
