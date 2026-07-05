# fractals

Fractal explorations with Quil.

## Setup

Ensure you have a new enough JDK installed.

Install [Leiningen](https://leiningen.org).

## Usage

Run any of the sketches with:

```bash
lein run <sketch>
```

Show available sketches with:

```bash
lein run
```

Available sketches:

- `cantor`
- `koch`
- `l-systems`
- `sierpinski`
- `tree`

The `l-systems` sketch requires a zero-based system index:

```bash
lein run l-systems <system-index>
```

Supported `l-systems` indexes:

- `0` quadratic koch island
- `1` quadratic snowflake curve
- `2` pentagons of pentagons
- `3` islands and lakes
- `4` sierpinski gasket with edge rewriting
- `5` hexagonal gosper curve with edge rewriting
- `6` tree with node rewriting
- `7` tree 2
- `8` tree 3
- `9` tree 4

## License

[CC0](https://creativecommons.org/publicdomain/zero/1.0), public domain.
