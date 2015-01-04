// Inject server variable
var pathToApp = window.pathToApp || 'public/js/app/'; // path for karma tests if undefined

angular.module('lvr')
    .constant('constants', {
        pathToApp: pathToApp,

        api: {
          signInWithCredentials: '/authenticate/credentials'
        },

        applicationUrls: {
            signIn: '/',
            signUp: '/sign-up',
            cloud: '/cloud',
            folderTree: '/folders/tree'
        },

        errorCodes: {
            userNotFound: 4004,
            accessDenied: 4002,
            userAlreadyExists: 4005
        },

        books: {
            minPagesPerBar: 50,
            maxBarsCount: 10
        },

        folders: {
            rootFolderName: "__root__",
            rootFolderId: 0
        }
    });
