package cz.mendelu.dto;

public class UserDTO {
    private String status;
    private String method;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "status='" + status + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
