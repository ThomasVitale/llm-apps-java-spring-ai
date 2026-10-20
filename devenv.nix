{ pkgs, lib, config, inputs, ... }:

{
  # https://devenv.sh/packages/
  packages = [
    pkgs.httpie
  ];

  # https://devenv.sh/languages/
  languages.java = {
    enable = true;
    jdk.package = pkgs.graalvmPackages.graalvm-ce;
  };

  # See full reference at https://devenv.sh/reference/options/
}
