package symphony

class TestForm<F : Fields>(
    override val fields: F,
    builder: FormActionsBuildingBlock<Map<String, String>, Any?>
) : Form<F, Map<String, String>, Any?>(
    heading = "Test Form",
    details = "This is a form for testing",
    fields = fields,
    config = FormConfig(exitOnSubmitted = false),
    builder
)