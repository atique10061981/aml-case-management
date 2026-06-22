# config-repo

Configuration files served by the AML Case Management Config Server.

## File naming

| Pattern | Applies to |
| --- | --- |
| `application.yml` | All services, all profiles |
| `application-{profile}.yml` | All services with `{profile}` active |
| `{service}.yml` | Specific service, all profiles |
| `{service}-{profile}.yml` | Specific service with `{profile}` active |

## Resolution order (highest precedence first)

1. `{service}-{profile}.yml`
2. `{service}.yml`
3. `application-{profile}.yml`
4. `application.yml`

## Production note

In production this directory would live in a separate Git repository
(typically `aml-cm-config`) and the Config Server would use its `git`
backend instead of `native`. Keeping it inline simplifies the dev loop
for this portfolio project.
