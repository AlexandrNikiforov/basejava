package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ExperienceDescription;
import ru.javawebinar.basejava.model.Experience;
import ru.javawebinar.basejava.model.Resume;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {

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

        List<String> qualificationsContent = new ArrayList<>(Arrays.asList(
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

        List<ExperienceDescription> experience = new ArrayList<>(Arrays.asList(
                Experience.builder()
                        .withCompanyName("Java Online Projects")
                        .withCompanyWebSite("https://javaops.ru/")
                        .withStartDate(LocalDate.of(2013, 10, 1))
                        .withEndDate(LocalDate.now())
                        .withPosition("Автор проекта")
                        .withDescription("Создание, организация и проведение Java онлайн проектов и стажировок.")
                        .build(),

                Experience.builder()
                        .withCompanyName("Wrike")
                        .withCompanyWebSite("https://www.wrike.com/")
                        .withStartDate(LocalDate.of(2014, 10, 1))
                        .withEndDate(LocalDate.of(2016, 1, 1))
                        .withPosition("Старший разработчик (backend)")
                        .withDescription("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                                "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
                        .build(),

                Experience.builder()
                        .withCompanyName("RIT Center")
                        .withCompanyWebSite("")
                        .withStartDate(LocalDate.of(2012, 4, 1))
                        .withEndDate(LocalDate.of(2014, 10, 1))
                        .withPosition("Java архитектор")
                        .withDescription("Организация процесса разработки системы ERP для разных окружений: релизная " +
                                "политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной " +
                                "части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                                "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco " +
                                "JLAN для online редактирование из браузера документов MS Office. Maven + plugin " +
                                "development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, " +
                                "OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, " +
                                "PL/Python")
                        .build(),

                Experience.builder()
                        .withCompanyName("Luxoft (Deutsche Bank)")
                        .withCompanyWebSite("https://luxoft.com")
                        .withStartDate(LocalDate.of(2010, 12, 1))
                        .withEndDate(LocalDate.of(2012, 4, 1))
                        .withPosition("Ведущий программист")
                        .withDescription("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, " +
                                "Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной " +
                                "части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа " +
                                "результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, " +
                                "ExtGWT (GXT), Highstock, Commet, HTML5.")
                        .build(),

                Experience.builder()
                        .withCompanyName("Yota")
                        .withCompanyWebSite("https://www.yota.ru/")
                        .withStartDate(LocalDate.of(2008, 6, 1))
                        .withEndDate(LocalDate.of(2012, 10, 1))
                        .withPosition("Ведущий специалист")
                        .withDescription("Дизайн и имплементация Java EE фреймворка для отдела " +
                                "\"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, " +
                                "JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга " +
                                "фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")
                        .build()
        ));

        List<ExperienceDescription> education = new ArrayList<>(Arrays.asList(
                Experience.builder()
                        .withCompanyName("Coursera")
                        .withCompanyWebSite("https://www.coursera.org/learn/progfun1")
                        .withStartDate(LocalDate.of(2013, 3, 1))
                        .withEndDate(LocalDate.of(2013, 5, 1))
                        .withPosition("")
                        .withDescription("\"Functional Programming Principles in Scala\" by Martin Odersky")
                        .build(),

                Experience.builder()
                        .withCompanyName("Luxoft")
                        .withCompanyWebSite("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366")
                        .withStartDate(LocalDate.of(2011, 3, 1))
                        .withEndDate(LocalDate.of(2011, 4, 1))
                        .withPosition("")
                        .withDescription("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"")
                        .build(),

                Experience.builder()
                        .withCompanyName("Siemens AG")
                        .withCompanyWebSite("http://www.siemens.ru/")
                        .withStartDate(LocalDate.of(2005, 1, 1))
                        .withEndDate(LocalDate.of(2005, 4, 1))
                        .withPosition("")
                        .withDescription("3 месяца обучения мобильным IN сетям (Берлин)")
                        .build()
        ));


        Resume resume01 = Resume.builder()
//                .withUuid("uuid01")
                .withFullName("Григорий Кислин")
                .withPhoneNumber("+7(921)855-0482")
                .withSkype("gkislin@yandex.ru")
                .withEmail("+7(921)855-0482")
                .withLinkedIn("https://www.linkedin.com/in/gkislin")
                .withGitHubProfile("https://github.com/gkislin")
                .withStackoverflow("https://stackoverflow.com/users/548473")
                .withHomePage("http://gkislin.ru/")
                .withObjective("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям")
                .withPersonal("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры.")
                .withAchievements(achievementsContent)
                .withQualifications(qualificationsContent)
                .withExperience(experience)
                .withEducation(education)
                .build();

        System.out.println(resume01);
    }
}
