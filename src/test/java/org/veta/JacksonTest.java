package org.veta;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.veta.jsonClasses.Student;

import java.io.File;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class JacksonTest {
    String jsonFile = "student.json";

    @DisplayName("Test of json file")
    @Test
    void jsonJacksonTest() throws Exception {
        File file = new File("src/test/resources/"+ jsonFile);
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(file, Student.class);
        assertThat(student.name).isEqualTo("Veta");
        assertThat(student.phone).isEqualTo(123456789);
        assertThat(student.isStudent).isEqualTo(true);
        assertThat(student.subjects[1]).isEqualTo("Art");
        assertThat(student.studentID.get("number")).isEqualTo("123456");
    }
}
