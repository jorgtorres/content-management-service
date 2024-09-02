package com.unionestate.cm.exception;

public class CMException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 2829285310593924158L;

    protected CMException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CMException(String message) {
        super(message);
    }

    public static class ObjectNotFound extends CMException {

        /**
         *
         */
        private static final long serialVersionUID = -7658251897023722934L;

        public ObjectNotFound(String string, Throwable cause) {
            super(string, cause);
        }

        public ObjectNotFound(String string) {
            super(string);
        }
    }

    public static class LatestVersionNotFound extends CMException {

        /**
         *
         */
        private static final long serialVersionUID = -7658251897023722935L;

        public LatestVersionNotFound(String string, Throwable cause) {
            super(string, cause);
        }

        public LatestVersionNotFound(String string) {
            super(string);
        }
    }

    public static class Unauthorized extends CMException {

        /**
         *
         */
        private static final long serialVersionUID = -8556296251739880811L;

        public Unauthorized(String string, Throwable cause) {
            super(string, cause);
        }
    }

    public static class Forbidden extends CMException {

        /**
         *
         */
        private static final long serialVersionUID = -8556296251739380811L;

        public Forbidden(String string, Throwable cause) {
            super(string, cause);
        }
    }

    public static class ServiceError extends CMException {

        /**
         *
         */
        private static final long serialVersionUID = -3220765297496574997L;

        public ServiceError(String string, Throwable cause) {
            super(string, cause);
        }
    }

    public static class NameAlreadyExists extends CMException {

        private static final long serialVersionUID = 8326338836024085767L;

        public NameAlreadyExists(String message) {
            super(message);
        }
    }

    public static class InvalidDocumentException extends CMException {

        /**
         *
         */
        private static final long serialVersionUID = 8841080579462161341L;

        public InvalidDocumentException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidDocumentException(String message) {
            super(message);
        }
    }

    public static class ObjectAlreadyExists extends CMException {

        /**
         *
         */
        private static final long serialVersionUID = 6540663776505970161L;

        public ObjectAlreadyExists(String message) {
            super(message);
        }
    }

    public static class DocumentCheckedOut extends CMException {

        /**
         *
         */
        private static final long serialVersionUID = -5300815686684266867L;

        public DocumentCheckedOut(String message) {
            super(message);
        }

    }

}
