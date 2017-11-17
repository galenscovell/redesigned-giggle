package galenscovell.sandbox.stateMachines;

import galenscovell.sandbox.ecs.component.StateComponent;


public enum CropAgent implements State<StateComponent> {
    SEED() {
        @Override
        public void enter(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
        }

        @Override
        public void update(StateComponent stateComponent) {

        }

        @Override
        public float getFrameRatio() {
            return 0;
        }

        @Override
        public String getName() {
            return "SEED";
        }
    },
    SPROUT() {
        @Override
        public void enter(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
        }

        @Override
        public void update(StateComponent stateComponent) {

        }

        @Override
        public float getFrameRatio() {
            return 0;
        }

        @Override
        public String getName() {
            return "SPROUT";
        }
    },
    IMMATURE() {
        @Override
        public void enter(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
        }

        @Override
        public void update(StateComponent stateComponent) {

        }

        @Override
        public float getFrameRatio() {
            return 0;
        }

        @Override
        public String getName() {
            return "IMMATURE";
        }
    },
    MATURE() {
        @Override
        public void enter(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Enter %s", getName()));
        }

        @Override
        public void exit(StateComponent stateComponent) {
            System.out.println(String.format("Crop: Exit %s", getName()));
        }

        @Override
        public void update(StateComponent stateComponent) {

        }

        @Override
        public float getFrameRatio() {
            return 0;
        }

        @Override
        public String getName() {
            return "MATURE";
        }
    }
}
