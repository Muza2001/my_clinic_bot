package academic.com.mapper;

import academic.com.dto.response.AttachmentResponse;
import academic.com.model.Attachment;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentResponse toResponse(Attachment attachment);

    Set<AttachmentResponse> toListResponse(Set<Attachment> attachments);

}
