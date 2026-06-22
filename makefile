# ============================================================
# AML Case Management — local infra control
# Run `make help` for the menu.
# ============================================================

.PHONY: help up down stop restart logs ps mongo obs elk qa all clean prune \
        verify ports

help:  ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?##' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-12s\033[0m %s\n", $$1, $$2}'

# ─── Profile shortcuts ───────────────────────────────────────

up:  ## Start core profile (Postgres, Redis, Kafka, etc.)
	docker compose up -d
	@echo "✓ Core infra up. Kafka UI → http://localhost:8090"

mongo:  ## Start MongoDB (needed from Step 16)
	docker compose --profile mongo up -d mongodb

obs:  ## Start Prometheus + Grafana + Jaeger
	docker compose --profile obs up -d
	@echo "✓ Observability up. Grafana → http://localhost:3000 (admin/admin)"

elk:  ## Start Elasticsearch + Logstash + Kibana (heavy, ~2.5 GB)
	docker compose --profile elk up -d
	@echo "✓ ELK up. Kibana → http://localhost:5601 (start time ~60s)"

qa:  ## Start SonarQube + its Postgres (heavy, ~2.5 GB)
	docker compose --profile qa up -d
	@echo "✓ SonarQube up. → http://localhost:9000 (admin/admin)"

all:  ## Start EVERYTHING (use sparingly — ~8 GB)
	docker compose --profile mongo --profile obs --profile elk --profile qa up -d

# ─── Lifecycle ───────────────────────────────────────────────

stop:  ## Stop all containers (volumes preserved)
	docker compose --profile mongo --profile obs --profile elk --profile qa stop

down:  ## Stop and remove containers (volumes preserved)
	docker compose --profile mongo --profile obs --profile elk --profile qa down

restart:  ## Restart core profile
	docker compose restart

clean:  ## Stop and WIPE volumes (data loss!)
	docker compose --profile mongo --profile obs --profile elk --profile qa down -v

prune:  ## Remove dangling Docker images/networks
	docker system prune -f

# ─── Inspection ──────────────────────────────────────────────

ps:  ## List running containers
	docker compose --profile mongo --profile obs --profile elk --profile qa ps

logs:  ## Tail all logs (Ctrl-C to exit)
	docker compose logs -f --tail=50

# ─── Verification ────────────────────────────────────────────

verify:  ## Smoke-test core services
	@echo "→ Postgres databases:"
	@docker exec aml-postgres psql -U $${POSTGRES_USER:-aml_admin} -d aml_admin -c "\l" | grep "^ aml_" || echo "  (none yet)"
	@echo "→ Redis ping:"
	@docker exec aml-redis redis-cli -a $${REDIS_PASSWORD:-aml_redis_pwd} ping 2>/dev/null
	@echo "→ Kafka topic list:"
	@docker exec aml-kafka kafka-topics --bootstrap-server localhost:9092 --list 2>/dev/null || echo "  (Kafka not ready yet)"

ports:  ## Show forwarded URLs (Codespaces-aware)
	@echo "Local URLs:"
	@echo "  Kafka UI       http://localhost:8090"
	@echo "  MailHog UI     http://localhost:8025"
	@echo "  Grafana        http://localhost:3000   (admin/admin)"
	@echo "  Prometheus     http://localhost:9090"
	@echo "  Kibana         http://localhost:5601"
	@echo "  Jaeger         http://localhost:16686"
	@echo "  SonarQube      http://localhost:9000   (admin/admin)"
	@echo ""
	@echo "In Codespaces, use the PORTS tab → right-click a port → 'Open in Browser'"
