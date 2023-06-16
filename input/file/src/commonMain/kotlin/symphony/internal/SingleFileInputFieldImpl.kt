package symphony.internal

import epsilon.FileBlob
import epsilon.serializers.FileBlobSerializer
import kotlinx.serialization.KSerializer
import symphony.Label
import symphony.SingleFileInputField
import symphony.internal.validators.CompoundValidator
import symphony.internal.validators.LambdaValidator
import symphony.internal.validators.RequirementValidator

@PublishedApi
internal class SingleFileInputFieldImpl(
    override val name: String,
    override val label: Label,
    override val hint: String,
    private val value: FileBlob?,
    override val isReadonly: Boolean,
    override val isRequired: Boolean,
    private val validator: ((FileBlob?) -> Unit)?
) : PlainDataField<FileBlob>(value), SingleFileInputField {

    override val cv = CompoundValidator(
        data, feedback,
        RequirementValidator(data, feedback, label.capitalizedWithoutAstrix(), isRequired),
        LambdaValidator(data, feedback, validator)
    )
}