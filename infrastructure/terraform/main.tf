locals {

  api_operations_json = jsondecode(file("../template/policies.json"))
  api_operations = flatten([for v in local.api_operations_json.policies : {
    "operation_id" = v.operationId,
    "api_name"     = "service-api",
    "xml_content"  = file("../template/${v.templateFile}"),
    "display_name" = v.displayName,
    "method"       = v.method,
    "url_template" = v.url_template,
    "description"  = v.description
  }])
}

module "apim_api" {
  source     = "git::https://github.com/hmcts/terraform-module-apim-api"
  env        = var.environment
  product    = "pip"
  department = "sds"

  api_name                  = "publication-api"
  api_revision              = "1"
  api_protocols             = ["https"]
  api_service_url           = var.service_url
  api_subscription_required = false
  api_content_format        = var.open_api_spec_content_format
  api_content_value         = var.open_api_spec_content_value

  policy_xml_content = file("../template/api-policy.xml")
  api_operations     = local.api_operations
}
