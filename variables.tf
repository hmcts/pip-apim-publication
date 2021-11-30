## Defaults
variable "product" {
  default = "pip"
}
variable "component" {
  default = "sds"
}
variable "location" {
  default = "UK South"
}
variable "env" {}
variable "subscription" {
  default = ""
}
variable "deployment_namespace" {
  default = ""
}
variable "common_tags" {
  type = map(string)
}
variable "team_name" {
  default = "PIP DevOps"
}
variable "team_contact" {
  default = "#vh-devops"
}

variable "service_url" {
  type        = string
  description = "Service URL"
  default     = ""
}
variable "open_api_spec_content_format" {
  type        = string
  description = "Open API Spec Content Format"
  default     = "openapi-link"
}
variable "open_api_spec_content_value" {
  type        = string
  description = "Open API Spec Content value"
  default     = "https://raw.githubusercontent.com/hmcts/reform-api-docs/master/docs/specs/pip-gateway-api.json"
}
variable "enable_mock_header_string" {
  type        = string
  description = ""
  default     = "<set-header name=\"_EnableMocks\" exists-action=\"override\"><value>true</value></set-header>"
}