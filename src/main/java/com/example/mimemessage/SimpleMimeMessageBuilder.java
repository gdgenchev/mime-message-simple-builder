package com.example.mimemessage;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

public class SimpleMimeMessageBuilder {

    private static final String FALLBACK_FILENAME_PREFIX = "file_";

    private JavaMailSender mailSender;
    private String from;
    private String subject;
    private String to;
    private String text;
    private boolean isHtml;
    private List<MultipartFile> files;

    public SimpleMimeMessageBuilder withMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        return this;
    }

    public SimpleMimeMessageBuilder withFrom(String from) {
        this.from = from;
        return this;
    }

    public SimpleMimeMessageBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public SimpleMimeMessageBuilder withTo(String to) {
        this.to = to;
        return this;
    }

    public SimpleMimeMessageBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public SimpleMimeMessageBuilder withHtml(String html) {
        this.text = html;
        this.isHtml = true;
        return this;
    }

    public SimpleMimeMessageBuilder withFiles(List<MultipartFile> files) {
        this.files = files;
        return this;
    }

    public MimeMessage build() throws MessagingException {
        if (mailSender == null) {
            throw new IllegalStateException("mailSender is not set");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(text, isHtml);

        addAttachments(helper, files);

        return message;
    }

    private void addAttachments(MimeMessageHelper mimeMessageHelper, List<MultipartFile> files) throws MessagingException {
        if (files == null) {
            return;
        }

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String originalFilename = file.getOriginalFilename();
            originalFilename = originalFilename != null ? originalFilename : FALLBACK_FILENAME_PREFIX + i;

            mimeMessageHelper.addAttachment(originalFilename, file);
        }
    }
}
