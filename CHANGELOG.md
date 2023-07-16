# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## `v1.1.0` - 16/07/2023

### Added

- World blacklisting
  - Via commands `/biomefog worldblacklist add` and `/biomefog worldblacklist remove`
  - Saved to `config/biome_fog/biome_fog_blacklisted_worlds.json`

### Changed

- Moved configuration files into the `config/biome_fog/` directory

### Fixed

- Lava fog taking biome fog color instead of normal red color
- Water fog not behaving in the same way as vanilla, with the fog start being close and gradually moving further away
  after entering the water
