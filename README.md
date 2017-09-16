# lein-ftp-static-deploy

A Leiningen plugin to deploy a directory to a directory on a remote server via FTP.

## Usage

To use lein-ftp-static-deploy, you will need to add a new `ftp` section to you project to your project.clj.

    :ftp { :host ~(System/getenv "FTP_DEPLOY_HOST") :user ~(System/getenv "FTP_DEPLOY_USER") :pass ~(System/getenv "FTP_DEPLOY_PASS") :port ~(or (System/getenv "FTP_DEPLOY_PORT") 21) :ftp-static-deploy {:remote-root "THE REMOTE DIRECTORY YOU WANT TO DEPLOY TO" :local-root "LOCAL DIRECTORY YOU WANT TO DEPLOY FROM"}}

Add the following to your `:plugins` vector:

![](https://clojars.org/lein-ftp-static-deploy/latest-version.svg)

### Environment Variables

* `FTP_DEPLOY_HOST`
* `FTP_DEPLOY_USER`
* `FTP_DEPLOY_PASS`
* `FTP_DEPLOY_PORT` - defaults to 21

## Deploy

You should now be able to deploy your website to the remote server using the following command:

    $ lein ftp-static-deploy

## License

Copyright © 2017 Joseph P. Hayes

Released under the MIT license.
