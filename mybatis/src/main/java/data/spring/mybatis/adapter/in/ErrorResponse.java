package data.spring.mybatis.adapter.in;

public record ErrorResponse(
        String message
) {
    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }
}
