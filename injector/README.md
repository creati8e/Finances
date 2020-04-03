Injector module acts like a proxy (or glue) between feature modules and
their dependencies. It's introduced to prevent direct modules dependency
on modules with their dependency implementations (such as
:**`core-impl`**).

This way when actual dependency implementation is
changed, feature modules are not recompilled because they are depends on
**`:injector`** module. Only **`:injector`** module is getting
recompilled.

**This module must be added as *implementation* dependency.**