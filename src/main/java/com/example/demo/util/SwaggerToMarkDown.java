package com.example.demo.util;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SwaggerToMarkDown {
    public static void main(String[] args) throws Exception {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Path outputFile = Paths.get("doc/后端接口文档"+dateStr);
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(new URL("http://localhost:8082/v2/api-docs"))
                .withConfig(config)
                .build();
        converter.toFile(outputFile);
    }
}
