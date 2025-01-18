# cos126webv2
Hugo instructions / specification for COS 126 web site.

# Under construction....

### Hugo Software 
- Requires Hugo:
  - On Mac: `brew install hugo`

- Requires git version that supports shallow:
  - On Mac: `brew install git`

- Make sure `hugo` and  `git` are in your `PATH`:
  - On Mac: `brew` automatically configures 

### Workflow - Editing and Generating a Web Site

- In order to preview modifications before deploying, probably easiest to work locally on your Mac
- `cd cos126web`
- To generate a local web server: `hugo server --cacheDir /tmp/hugo`
  - The `--cacheDir /tmp/hugo` is optional.  I like to specify the cache so I know where to delete it just in case
  - Hugo will compile the markdown and echo  a local URL
  - You can update the markdown and Hugo will automatically re-compile so you can view changes
  - For example:
```
> pwd
/Users/alankaplan/Dev/cos126web
> hugo server --cacheDir /tmp/hugo
Start building sites … 
hugo v0.87.0+extended darwin/amd64 BuildDate=unknown

                   |  EN   
-------------------+-------
  Pages            |   70  
  Paginator pages  |    2  
  Non-page files   |   61  
  Static files     | 1665  
  Processed images |   45  
  Aliases          |    5  
  Sitemaps         |    1  
  Cleaned          |    0  

Built in 3773 ms
Watching for changes in /Users/alankaplan/Dev/cos126web/{assets,content,layouts,static}
Watching for config changes in /Users/alankaplan/Dev/cos126web/config/_default, /Users/alankaplan/Dev/cos126web/go.mod
Environment: "development"
Serving pages from memory
Running in Fast Render Mode. For full rebuilds on change: hugo server --disableFastRender
Web Server is available at http://localhost:1313/courses/archive/spr22/cos126/ (bind address 127.0.0.1)
Press Ctrl+C to stop
```
  
### Setting up Unix JumpProxy
- On Mac, in your `~/.ssh` directory
   - Create the file `config` with user `rw` only privs: `-rw-------  config`
   - The `config`  files contains:
   ```
   Host *
       ControlPath ~/.ssh/controlsocket/%C
       ControlMaster auto

   Host portal
       HostName portal.cs.princeton.edu
 
   Host cos126
       Hostname portal.cs.princeton.edu
       ProxyJump <netid>@portal.cs.princeton.edu
       User cos126
   ```
- After creating the `config` file:  `mkdir ~/.ssh/controlsocket` and `chmod 700 ~/.ssh/controlsocket` . This will create the directory where each multiplexed SSH socket is written.

From the Terminal, you can now:
- `ssh <netid>@portal`
- `ssh cos126`

Etc.  But you only need to do this once!  You won't be prompted for passwords for when you `rsync` (see below)

### Workflow

- On Mac, generate the HTML in a directory named `public`
```
> pwd
/Users/alankaplan/Dev/cos126web
> hugo   --cacheDir /tmp/hugo -d public
> ls -ld public
drwxr-xr-x  37 alankaplan  staff  1184 Sep  1 10:43 public/
```

- To publish a newly generated site, use `rsync`:
```
> pwd
/Users/alankaplan/Dev/cos126web
> rsync -azu --progress --delete --exclude-from 'exclude.txt' public/ cos126:public_html/
```

- To publish incremental changes, use `rsync` with `--size-only` (which only updates files whose file sizes differ):
```
> pwd
/Users/alankaplan/Dev/cos126web
rsync -azu --progress --size-only --delete --exclude 'static' public/ cos126:public_html/
```

### Directories
- `config_default` 
  - `config.yaml` - semester base URL
  - `menus.yaml` - web site menus and order (by weight)
- `layouts`
  - `shortcodes` - useful Hugo shortcodes - note these need to be cleaned up to generalize semester dependencies
- `content` - web site markdown files
- `static` - not maintained on Git

### Before each semester
- There are two md files - schedule-spr.md and schedule-fall-md. Copy one to schedule.md.
- Update Atomic (final assessment date)
- Update hugo.md - semester details and including survey  url
