import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        for (int i = 0; i < this.storage.length; i++) {
            if (this.storage[i] == null) {
                this.storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (Resume resume : this.storage) {
            if (resume != null && resume.toString().equals(uuid)) {

                return resume;
            }
        }

        return null;
    }

    void delete(String uuid) {
        int i = 0;
        for (; i < this.storage.length; i++) {
            if (this.storage[i].toString().equals(uuid)) {
                this.storage[i] = null;
                break;
            }
        }
        i++;
        for (; i < this.storage.length; i++) {
            this.storage[i - 1] = this.storage[i];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.stream(storage)
                .filter(Objects::nonNull)
                .toArray(Resume[]::new);
    }

    int size() {
        return (int) Arrays.stream(storage)
                .filter(Objects::nonNull)
                .count();
    }
}
