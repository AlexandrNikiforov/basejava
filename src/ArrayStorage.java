/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (Resume resume : this.storage) {
            resume = null;
        }
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
        for (int i = 0; i < this.storage.length; i++) {
            if (this.storage[i].toString() == uuid) {

                return storage[i];
            }
        }

        return null;
    }

    void delete(String uuid) {
        int i = 0;
        for (; i < this.storage.length; i++) {
            if (this.storage[i].toString() == uuid) {
                this.storage[i] = null;
                break;
            }
            i++;
            for (; i < this.storage.length; i++) {
                this.storage[i - 1] = this.storage[i];
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        return 0;
    }
}
