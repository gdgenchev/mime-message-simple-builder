# SimpleMimeMessageBuilder

A utility class for declarative creation of email messages in Java Spring using Builder Pattern.

## Without `SimpleMimeMessageBuilder`

When creating a `MimeMessage` manually, each part of the email must be set using MimeMessageHelper setter methods.

```java
MimeMessage message = mailSender.createMimeMessage();
MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

helper.setFrom("from@example.com");
helper.setTo("to@example.com");
helper.setSubject("Subject");
helper.setText("Email content", true);

for (int i = 0; i < files.size(); i++) {
    String filename = files.get(i).getOriginalFilename();
    helper.addAttachment(filename != null ? filename : "file_" + i, files.get(i));
}
```

## With `SimpleMimeMessageBuilder`

Using `SimpleMimeMessageBuilder`, you can create a message in a more declarative and easy way without working with the
MimeMessageHelper setters directly.

```java
MimeMessage message = new SimpleMimeMessageBuilder()
    .withMailSender(mailSender)
    .withFrom("from@example.com")
    .withTo("to@example.com")
    .withSubject("Subject")
    .withHtml("<h1>Email content</h1>") // or .withText("Email content")
    .withFiles(files)
    .build();
```

## Usage

You can directly clone the class and easily extend it with more fields if needed.