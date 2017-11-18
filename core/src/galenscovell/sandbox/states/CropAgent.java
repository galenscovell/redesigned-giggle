package galenscovell.sandbox.states;

import galenscovell.sandbox.entities.components.StateComponent;


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
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "SEED";
        }

        @Override
        public int getId() {
            return 0;
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
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "SPROUT";
        }

        @Override
        public int getId() {
            return 1;
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
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "IMMATURE";
        }

        @Override
        public int getId() {
            return 2;
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
        public void update(float delta, StateComponent stateComponent) {

        }

        @Override
        public String getName() {
            return "MATURE";
        }

        @Override
        public int getId() {
            return 3;
        }
    }
}
