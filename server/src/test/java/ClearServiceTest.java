import org.junit.Test;

import services.clear.ClearService;
import services.message.MessageResponse;

public class ClearServiceTest {

    @Test
    public void printError() {
        ClearService clearService = new ClearService();
        MessageResponse messageResponse = clearService.clear();
        System.out.println(messageResponse.getMessage());
    }
}
