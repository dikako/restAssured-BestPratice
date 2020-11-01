package logallure;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUser {
	private int page;
}
