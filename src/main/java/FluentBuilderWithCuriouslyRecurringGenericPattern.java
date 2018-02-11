/**
 * https://en.wikipedia.org/wiki/Talk%3ACuriously_recurring_template_pattern
 * Example how we can use this patter for creating base builder and how to use it with inheritance.
 */
public class FluentBuilderWithCuriouslyRecurringGenericPattern {
    public static void main(String[] args) {
        SingleRequest.newBuilder()
                .withCommonPart(new Request.RequestCommonPart())
                .withSingleRequestPart(new SingleRequest.SingleRequestSpecificPart())
                .build();

        MultiRequest.newBuilder()
                .withCommonPart(new Request.RequestCommonPart())
                .withMultiRequestPart(new MultiRequest.MultiRequestSpecificPart())
                .build();
    }

    static class SingleRequest extends Request {
        private final SingleRequestSpecificPart singlRequestSpecificPart;

        SingleRequest(Builder builder) {
            super(builder);
            this.singlRequestSpecificPart = builder.singleRequestSpecificPart;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder extends Request.Builder<Builder, SingleRequest> {
            private SingleRequestSpecificPart singleRequestSpecificPart;

            public Builder withSingleRequestPart(SingleRequestSpecificPart part) {
                this.singleRequestSpecificPart = part;
                return this;
            }

            @Override
            public SingleRequest build() {
                return new SingleRequest(this);
            }
        }

        static class SingleRequestSpecificPart {
            //single request specific
        }
    }

    static class MultiRequest extends Request {
        private final MultiRequestSpecificPart multiRequestSpecificPart;

        MultiRequest(Builder builder) {
            super(builder);
            this.multiRequestSpecificPart = builder.multiRequestSpecificPart;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder extends Request.Builder<Builder, MultiRequest> {
            private MultiRequestSpecificPart multiRequestSpecificPart;

            public Builder withMultiRequestPart(MultiRequestSpecificPart part) {
                this.multiRequestSpecificPart = part;
                return this;
            }

            @Override
            public MultiRequest build() {
                return new MultiRequest(this);
            }
        }

        static class MultiRequestSpecificPart {
            //single request specific
        }
    }

    static abstract class Request {
        private final RequestCommonPart commonPart;

        protected Request(Builder builder) {
            this.commonPart = builder.commonPart;
        }

        abstract static class Builder<T extends Builder<T, K>, K extends Request> {
            private RequestCommonPart commonPart;

            public T withCommonPart(RequestCommonPart commonPart) {
                this.commonPart = commonPart;
                return (T) this;
            }

            public abstract K build();
        }

        static class RequestCommonPart {
            //common for all requests
        }
    }
}


