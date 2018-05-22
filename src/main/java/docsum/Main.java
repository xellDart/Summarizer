package docsum;

import com.jsoniter.output.JsonStream;
import docsum.deal.SummaryDeal;
import io.javalin.Javalin;

class Data {
    String img;
    Data(String img) {
        this.img = img;
    }
}

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.start(7000);

        app.post("/summarize", ctx -> {
            Data b64 = new Data(ctx.body());
            SummaryDeal summary = new SummaryDeal();
            ctx.result(JsonStream.serialize(summary.Summary(b64.img, 30)));
        });
    }
}
