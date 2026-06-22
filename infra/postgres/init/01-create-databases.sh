#!/bin/bash
# ============================================================
# Postgres init — runs ONCE when the data volume is empty.
# Creates one database + dedicated user per future microservice.
# Re-run by wiping volumes: `make clean && make up`
# ============================================================
set -euo pipefail

SERVICES=(
  "aml_customer:customer_svc:customer_pwd"
  "aml_transaction:transaction_svc:transaction_pwd"
  "aml_alert:alert_svc:alert_pwd"
  "aml_case:case_svc:case_pwd"
  "aml_sar:sar_svc:sar_pwd"
  "aml_notification:notification_svc:notification_pwd"
  "aml_reporting:reporting_svc:reporting_pwd"
  "aml_auth:auth_svc:auth_pwd"
)

for entry in "${SERVICES[@]}"; do
  IFS=':' read -r DB_NAME DB_USER DB_PASSWORD <<< "$entry"
  echo "Creating database '$DB_NAME' with owner '$DB_USER'..."
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
      CREATE USER $DB_USER WITH PASSWORD '$DB_PASSWORD';
      CREATE DATABASE $DB_NAME OWNER $DB_USER;
      GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;
EOSQL
done

echo "✓ All AML databases initialized."
