
<?php $__env->startSection("content"); ?>

<div class="row">
                            <div class="col-12">
                                <div class="card">
                                    <div class="card-body">

                                        <h4 class="card-title">Textual inputs</h4>
                                        
                                        

                                        <form method="POST" action="<?php echo e(route('chlgs')); ?>" >
                                            <?php echo csrf_field(); ?>
                                            
                                            <div class="row mb-3">
                                            <label for="example-text-input" class="col-sm-2 col-form-label">Challenge No</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" placeholder="Artisanal kale" id="regno" name="regno">
                                            </div>
                                        </div>

                                        <!-- end row -->
                                        <div class="row mb-3">
                                            <label for="example-search-input" class="col-sm-2 col-form-label">AttemptDuration</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" placeholder="How do I shoot web" id="schoolname" name="schoolname">
                                            </div>
                                        </div>
                                        <!-- end row -->
                                        <div class="row mb-3">
                                            <label for="example-email-input" class="col-sm-2 col-form-label">No Of Questions</label> 
                                            <div class="col-sm-10">
                                                <input class="form-control" type="email" placeholder="bootstrap@example.com" id="email" name="email">
                                            </div>
                                        </div>
                                        <!-- end row -->
                                        <div class="row mb-3">
                                            <label for="example-url-input" class="col-sm-2 col-form-label">OverallMark</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" placeholder="https://getbootstrap.com" id="district" name="district">
                                            </div>
                                        </div>
                                        <!-- end row -->
                                        <div class="row mb-3">
                                            <label for="example-text-input" class="col-sm-2 col-form-label">Opendate</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" placeholder="Artisanal kale" id="regno" name="regno">
                                            </div>
                                        </div>

                                        <div class="row mb-3">
                                            <label for="example-text-input" class="col-sm-2 col-form-label">Closedate No</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="text" placeholder="Artisanal kale" id="regno" name="regno">
                                            </div>
                                        </div>
                                        
                                        </div>
                                        <div class="mb-0">
                                                <div>
                                                    <button type="submit" class="btn btn-primary waves-effect waves-light me-1">
                                                        Submit
                                                    </button>
                                                    <button type="reset" class="btn btn-secondary waves-effect">
                                                        Cancel
                                                    </button>
                                                </div>
                                            </div>
                                        <!-- end row -->
                                                </form>
                                    </div>
                                </div>
                            </div> <!-- end col -->
                        </div>
                        <!-- end row -->

                        
                           
                        
                        <!-- end row -->

                        
  
                                                            <?php $__env->stopSection(); ?>
<?php echo $__env->make("layouts.dash", \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\xampp\htdocs\Laravel\Math\resources\views/challenge.blade.php ENDPATH**/ ?>