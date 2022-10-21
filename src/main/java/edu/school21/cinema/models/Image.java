package edu.school21.cinema.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

@Getter
@Setter
@NoArgsConstructor
public class Image {
    private Long id;
    private Long userId;
    private String name;
    private Long size;
    private String readableSize;
    private String mime;

    public Image(Long id, Long userId, String name, Long size, String mime) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.size = size;
        this.mime = mime;
        this.readableSize = FileUtils.byteCountToDisplaySize(size);
    }

    public void setSize(Long size) {
        this.size = size;
        setReadableSize();
    }

    public void setReadableSize() {
        this.readableSize = FileUtils.byteCountToDisplaySize(this.size);
    }
}
