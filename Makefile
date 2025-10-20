SHELL := /bin/bash

.PHONY: run init

run:
	@bash scripts/run.sh $(filter-out $@,$(MAKECMDGOALS))

init:
	@bash scripts/init.sh $(filter-out $@,$(MAKECMDGOALS))

%:
	@:
