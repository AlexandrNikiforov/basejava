package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.BulletedListSection;
import ru.javawebinar.basejava.model.ContactName;
import ru.javawebinar.basejava.model.Organization;
import ru.javawebinar.basejava.model.OrganizationSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionName;
import ru.javawebinar.basejava.model.SingleLineSection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactName, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeCollection(dos, resume.getSections().entrySet(), entry -> {
                SectionName type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {

                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((SingleLineSection) section).getContent());
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((BulletedListSection) section).getContentList(),
                                dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((OrganizationSection) section).getOrganizations(), org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getCompanyWebSite());
                            writeCollection(dos, org.getPositions(), pos -> {
                                writeLocalDate(dos, pos.getStartDate());
                                writeLocalDate(dos, pos.getEndDate());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection,
                                     ElementWriter<T> elementWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            elementWriter.write(item);
        }
    }

    private interface ElementWriter<T> {
        void write(T item) throws IOException;
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = Resume.builder()
                    .withContactsImplementation(new EnumMap<>(ContactName.class))
                    .withSectionsImplementation(new EnumMap<>(SectionName.class))
                    .withUuid(uuid)
                    .withFullName(fullName)
                    .build();
            readItems(dis, () -> resume.addContact(ContactName.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionName sectionName = SectionName.valueOf(dis.readUTF());
                resume.addSection(sectionName, readSection(dis, sectionName));
            });

            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionName sectionName) throws IOException {
        switch (sectionName) {

            case PERSONAL:
            case OBJECTIVE:
                return new SingleLineSection(dis.readUTF());
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                return new BulletedListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(
                        readList(dis, () -> Organization.builder()
                                .homePage(dis.readUTF(), dis.readUTF())
                                .withExperience(readList(dis, () ->
                                        Organization.Position.experienceItemBuilder()
                                                .withStartDate(readLocalDate(dis))
                                                .withEndDate(readLocalDate(dis))
                                                .withTitle(dis.readUTF())
                                                .withDescription(dis.readUTF())
                                                .build()
                                ))
                                .build())

                );
            default:
                throw new IllegalStateException();
        }
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private interface ElementProcessor {
        void process() throws IOException;
    }
}
