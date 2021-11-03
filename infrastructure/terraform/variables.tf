variable "prefix" {}
variable "product" {}
variable "environment" {}
variable "location" {}
variable "builtFrom" {
  type        = string
  description = "Build pipeline"
}
variable "service_url" {
  type        = string
  description = "Service URL"
}
variable "open_api_spec_content_format" {
  type        = string
  description = "Open API Spec Content Format"
}
variable "open_api_spec_content_value" {
  type        = string
  description = "Open API Spec Content value"
}