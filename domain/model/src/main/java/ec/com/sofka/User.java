package ec.com.sofka;

public class User {
    private String id;

    private String name;

    private String documentId;

    public User(String name, String documentId) {
        this.name = name;
        this.documentId = documentId;
    }

    public User(String id, String name, String documentId) {
        this.id = id;
        this.name = name;
        this.documentId = documentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
