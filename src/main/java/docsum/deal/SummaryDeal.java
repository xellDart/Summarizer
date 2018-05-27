package docsum.deal;

import docsum.Model.Result;
import docsum.summarizer.DocumentSummarizer;
import docsum.summarizer.KeywordExtractor;
import docsum.summarizer.SentencePreprocessor;
import docsum.summarizer.SentenceSegmenter;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.LogOutputStream;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SummaryDeal {

    private DocumentSummarizer summarizer;
    private KeywordExtractor extractor;

    private void b46toImg(String data) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imgBytes = decoder.decodeBuffer(data);

            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
            writeImage(bufImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeImage(BufferedImage image) {
        String path = System.getProperty("user.dir") + "/src/main/resources/";
        try {
            File imgOutFile = new File(path + "text.jpg");
            ImageIO.write(image, "jpg", imgOutFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getText() throws IOException, TimeoutException, InterruptedException {
        String file = System.getProperty("user.dir") + "/src/main/resources/ocr.R";
        StringBuilder data = new StringBuilder();
        new ProcessExecutor().command("Rscript", file)
                .redirectOutput(new LogOutputStream() {
                    @Override
                    protected void processLine(String line) {
                        data.append(line);
                    }
                }).execute();
        System.out.print(data.toString());
        String[] parts = data.toString().split("webp");
        return parts[1];
    }

    private Result summarize(String data, int percentage) {
        String summary = summarizer.summarize(data, percentage);
        String keywords = extractor.extract(summary);
        Result result = new Result();
        result.setResult(summary);
        result.setKeywords(keywords);
        return result;
    }

    private void initSummarize() {
        SentenceSegmenter seg = new SentenceSegmenter();
        SentencePreprocessor prep = new SentencePreprocessor();
        summarizer = new DocumentSummarizer(seg, prep);
        extractor = new KeywordExtractor(seg, prep);
    }


    public Result Summary(String b64, int length) {
        String data_to_process = "";
        b46toImg(b64);
        initSummarize();
        try {
            data_to_process = getText();
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        return summarize(data_to_process, length);
    }

}
