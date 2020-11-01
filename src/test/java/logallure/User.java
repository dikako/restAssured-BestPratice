package logallure;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    String name;
    String job;
}