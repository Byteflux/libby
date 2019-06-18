# Libby

A runtime dependency management library for plugins running in Java-based Minecraft
server platforms.

Libraries can be downloaded from Maven repositories (or direct URLs) into a plugin's data
folder, relocated and then loaded into the plugin's classpath at runtime.

### Why use runtime dependency management?

Due to file size constraints on plugin hosting services like SpigotMC, some plugins with
bundled dependencies become too large to be uploaded.

Using runtime dependency management, dependencies are downloaded and cached by the server
and don't need to be bundled with the plugin, which significantly reduces the size of the
plugin jar.

A smaller plugin jar also means shorter download times and less network strain for authors
who self-host their plugins on servers with limited bandwidth.

## Credits

Special thanks to:

* [Luck](https://github.com/lucko) for [LuckPerms](https://github.com/lucko/LuckPerms)
  and its dependency management system which was the original inspiration for this project
  and another thanks for [jar-relocator](https://github.com/lucko/jar-relocator) which is
  used by Libby to perform jar relocations.
* [Glare](https://github.com/darbyjack) for convincing me that I should publish this
  library instead of letting it sit around collecting dust :)
